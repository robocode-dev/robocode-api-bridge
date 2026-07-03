import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;

import java.io.ByteArrayInputStream;

/**
 * Utility class for bytecode transformation of robot classes to ensure proper termination.
 * Replaces infinite loops (while(true)) with condition-based loops (while(getEnergy() >= 0)).
 * <p>
 * The transformation is applied at wrap time (when the bot directory is generated), and the
 * transformed class file is written into the bot directory, where it shadows the original class
 * in the robot jar by preceding it on the classpath. Redefining an already loaded class at
 * runtime is not possible on modern JVMs without an instrumentation agent.
 */
public class RobotMethodReplacer {

    /**
     * Transforms class bytecode to replace infinite loops with energy-based loops.
     *
     * @param classBytes The bytecode to transform
     * @param className  The name of the class
     * @return The transformed bytecode or null if no transformation was performed
     * @throws BytecodeTransformException If an error occurs during transformation
     */
    public static byte[] transformClassBytes(byte[] classBytes, String className) throws BytecodeTransformException {
        try {
            JavaClass javaClass = parseClass(classBytes, className);
            ClassGen classGen = new ClassGen(javaClass);
            ConstantPoolGen cpg = classGen.getConstantPool();

            boolean modified = transformRunMethod(classGen, cpg, className);

            if (modified) {
                return classGen.getJavaClass().getBytes();
            }
            return null;
        } catch (Exception e) {
            throw new BytecodeTransformException("Error during bytecode transformation", e);
        }
    }

    /**
     * Parses class bytecode into a JavaClass object.
     *
     * @param classBytes The bytecode to parse
     * @param className  The name of the class
     * @return The parsed JavaClass
     * @throws Exception If parsing fails
     */
    private static JavaClass parseClass(byte[] classBytes, String className) throws Exception {
        ClassParser parser = new ClassParser(new ByteArrayInputStream(classBytes), className);
        return parser.parse();
    }

    /**
     * Transforms the run() method in a class to replace infinite loops.
     *
     * @param classGen  The ClassGen object representing the class
     * @param cpg       The ConstantPoolGen for the class
     * @param className The name of the class
     * @return True if the method was modified, false otherwise
     */
    private static boolean transformRunMethod(ClassGen classGen, ConstantPoolGen cpg, String className) {
        for (Method method : classGen.getJavaClass().getMethods()) {
            if (isRunMethod(method)) {
                MethodGen methodGen = new MethodGen(method, className, cpg);
                boolean modified = replaceInfiniteLoops(methodGen, cpg, className);

                if (modified) {
                    // Update the method with the modified instruction list
                    methodGen.setMaxStack();
                    methodGen.setMaxLocals();
                    classGen.replaceMethod(method, methodGen.getMethod());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a method is the run() method.
     *
     * @param method The method to check
     * @return True if the method is run(), false otherwise
     */
    private static boolean isRunMethod(Method method) {
        return method.getName().equals("run") && method.getSignature().equals("()V");
    }

    /**
     * Replaces infinite loops in a method with energy-based loops.
     *
     * @param methodGen The MethodGen object representing the method
     * @param cpg       The ConstantPoolGen for the class
     * @param className The name of the class
     * @return True if any loops were replaced, false otherwise
     */
    private static boolean replaceInfiniteLoops(MethodGen methodGen, ConstantPoolGen cpg, String className) {
        InstructionList instructionList = methodGen.getInstructionList();
        if (instructionList == null) {
            return false; // abstract or native method that has no code
        }
        InstructionHandle[] handles = instructionList.getInstructionHandles();

        for (int i = 0; i < handles.length - 1; i++) {
            if (isInfiniteLoopPattern(handles[i], handles[i + 1])) {
                replaceWithEnergyCheck(instructionList, handles[i], handles[i + 1], cpg, className);
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an instruction pair forms an infinite loop pattern.
     *
     * @param currentHandle The current instruction handle
     * @param nextHandle    The next instruction handle
     * @return True if the pattern represents an infinite loop, false otherwise
     */
    private static boolean isInfiniteLoopPattern(InstructionHandle currentHandle, InstructionHandle nextHandle) {
        Instruction currentInst = currentHandle.getInstruction();
        Instruction nextInst = nextHandle.getInstruction();

        // `while(true)` compiled with an explicit condition check is either
        // `iconst_1; ifeq <exit>` (branch out of the loop when the condition is false) or
        // `iconst_1; ifne <loop start>` (branch back when the condition is true).
        return currentInst instanceof ICONST &&
                ((ICONST) currentInst).getValue().intValue() == 1 &&
                (nextInst instanceof IFEQ || nextInst instanceof IFNE);
    }

    /**
     * Replaces an infinite loop with an energy-based check.
     *
     * @param instructionList The instruction list to modify
     * @param currentHandle   The current instruction handle
     * @param nextHandle      The next instruction handle
     * @param cpg             The ConstantPoolGen for the class
     * @param className       The name of the class
     */
    private static void replaceWithEnergyCheck(
            InstructionList instructionList,
            InstructionHandle currentHandle,
            InstructionHandle nextHandle,
            ConstantPoolGen cpg,
            String className) {

        InstructionList newInstructions = createEnergyCheckInstructions(
                cpg, className, (IfInstruction) nextHandle.getInstruction());

        // Insert the replacement before deleting the old instructions, so that branch
        // instructions targeting the old instructions (the loop back edge) can be
        // redirected to the start of the replacement.
        InstructionHandle newStart = instructionList.insert(currentHandle, newInstructions);
        try {
            instructionList.delete(currentHandle, nextHandle);
        } catch (TargetLostException e) {
            handleLostTargets(e, newStart);
        }
    }

    /**
     * Creates instructions for checking if energy is greater than or equal to zero.
     *
     * @param cpg           The ConstantPoolGen for the class
     * @param className     The name of the class
     * @param ifInstruction The condition branch being replaced
     * @return A new instruction list with the energy check
     */
    private static InstructionList createEnergyCheckInstructions(
            ConstantPoolGen cpg, String className, IfInstruction ifInstruction) {

        InstructionList newInstructions = new InstructionList();

        // this.getEnergy()
        newInstructions.append(new ALOAD(0)); // load 'this'
        newInstructions.append(new INVOKEVIRTUAL(cpg.addMethodref(
                className,
                "getEnergy",
                "()D")));

        // Compare with 0.0
        newInstructions.append(new DCONST(0.0));
        newInstructions.append(new DCMPG());

        // Preserve the branch semantics of the replaced check: `iconst_1; ifeq <exit>` branches
        // out of the loop when the condition is false, so the replacement branches out when
        // energy < 0. `iconst_1; ifne <loop start>` branches back when the condition is true,
        // so the replacement branches back when energy >= 0.
        if (ifInstruction instanceof IFEQ) {
            newInstructions.append(new IFLT(ifInstruction.getTarget()));
        } else {
            newInstructions.append(new IFGE(ifInstruction.getTarget()));
        }

        return newInstructions;
    }

    /**
     * Handles targets that were lost during instruction replacement.
     *
     * @param e         The TargetLostException
     * @param newTarget The new target for instructions
     */
    private static void handleLostTargets(TargetLostException e, InstructionHandle newTarget) {
        InstructionHandle[] targets = e.getTargets();
        for (InstructionHandle target : targets) {
            InstructionTargeter[] targeters = target.getTargeters();
            for (InstructionTargeter targeter : targeters) {
                targeter.updateTarget(target, newTarget);
            }
        }
    }

    /**
     * Exception thrown when there's an issue transforming bytecode.
     */
    public static class BytecodeTransformException extends Exception {
        public BytecodeTransformException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
