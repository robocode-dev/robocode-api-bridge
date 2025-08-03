import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for bytecode transformation of robot classes to ensure proper termination.
 * Replaces infinite loops (while(true)) with condition-based loops (while(getEnergy() >= 0)).
 */
public class RobotMethodReplacer {

    private static final Logger LOGGER = Logger.getLogger(RobotMethodReplacer.class.getName());

    /**
     * Transforms a robot class to replace "while(true)" with "while(getEnergy() >= 0)"
     * in the run() method.
     *
     * @param robotClass The loaded robot class to modify
     * @return The transformed class or null if no transformation was performed
     */
    public static Class<?> transformRobotClass(Class<?> robotClass) {
        if (robotClass == null) {
            LOGGER.warning("Cannot transform null robot class");
            return null;
        }

        try {
            String className = robotClass.getName();
            byte[] classBytes = extractClassBytes(robotClass);
            byte[] transformedBytes = transformClassBytes(classBytes, className);

            if (transformedBytes == null) {
                // No transformation needed or possible
                return null;
            }

            return defineTransformedClass(className, transformedBytes, robotClass.getClassLoader());
        } catch (ClassBytecodeException e) {
            LOGGER.log(Level.WARNING, "Failed to extract bytecode for class: " + robotClass.getName(), e);
            return null;
        } catch (BytecodeTransformException e) {
            LOGGER.log(Level.WARNING, "Failed to transform class: " + robotClass.getName(), e);
            return null;
        } catch (ClassDefinitionException e) {
            LOGGER.log(Level.WARNING, "Failed to define transformed class: " + robotClass.getName(), e);
            return null;
        }
    }

    /**
     * Extracts the bytecode of a class.
     *
     * @param clazz The class to extract bytecode from
     * @return The bytecode as a byte array
     * @throws ClassBytecodeException If the bytecode cannot be extracted
     */
    private static byte[] extractClassBytes(Class<?> clazz) throws ClassBytecodeException {
        String resourceName = clazz.getName().replace('.', '/') + ".class";
        var classLoader = clazz.getClassLoader();

        try (var inputStream = classLoader.getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new ClassBytecodeException(
                        "Could not find class resource: " + resourceName,
                        null);
            }

            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new ClassBytecodeException(
                    "Failed to read bytecode for class: " + clazz.getName(),
                    e);
        }
    }

    /**
     * Transforms class bytecode to replace infinite loops with energy-based loops.
     *
     * @param classBytes The bytecode to transform
     * @param className  The name of the class
     * @return The transformed bytecode or null if no transformation was performed
     * @throws BytecodeTransformException If an error occurs during transformation
     */
    private static byte[] transformClassBytes(byte[] classBytes, String className) throws BytecodeTransformException {
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

        return currentInst instanceof ICONST &&
                ((ICONST) currentInst).getValue().intValue() == 1 &&
                nextInst instanceof IfInstruction;
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
                cpg, className, ((BranchInstruction) nextHandle.getInstruction()).getTarget());

        try {
            instructionList.delete(currentHandle, nextHandle);
            instructionList.insert(currentHandle, newInstructions);
        } catch (TargetLostException e) {
            handleLostTargets(e, currentHandle);
        }
    }

    /**
     * Creates instructions for checking if energy is greater than or equal to zero.
     *
     * @param cpg       The ConstantPoolGen for the class
     * @param className The name of the class
     * @param target    The branch target
     * @return A new instruction list with the energy check
     */
    private static InstructionList createEnergyCheckInstructions(
            ConstantPoolGen cpg, String className, InstructionHandle target) {

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
        newInstructions.append(new IFLT(target));

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
     * Exception thrown when there's an issue extracting bytecode from a class.
     */
    private static class ClassBytecodeException extends Exception {
        public ClassBytecodeException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Exception thrown when there's an issue transforming bytecode.
     */
    private static class BytecodeTransformException extends Exception {
        public BytecodeTransformException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Exception thrown when there's an issue defining a transformed class.
     */
    private static class ClassDefinitionException extends Exception {
        public ClassDefinitionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Defines a new class with the transformed bytecode.
     *
     * @param className   The name of the class
     * @param classBytes  The transformed bytecode
     * @param classLoader The class loader to use
     * @return The defined class
     * @throws ClassDefinitionException If the class cannot be defined
     */
    private static Class<?> defineTransformedClass(String className, byte[] classBytes, ClassLoader classLoader)
            throws ClassDefinitionException {
        if (classLoader == null) {
            throw new ClassDefinitionException("ClassLoader cannot be null", null);
        }

        try {
            // Use reflection to access the defineClass method of ClassLoader
            java.lang.reflect.Method defineClassMethod = ClassLoader.class.getDeclaredMethod(
                    "defineClass", String.class, byte[].class, int.class, int.class);
            defineClassMethod.setAccessible(true);

            // Define the new class with the transformed bytecode
            return (Class<?>) defineClassMethod.invoke(
                    classLoader, className, classBytes, 0, classBytes.length);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new ClassDefinitionException("Could not access defineClass method", e);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new ClassDefinitionException("Could not invoke defineClass method", e);
        } catch (InvocationTargetException e) {
            throw new ClassDefinitionException("Exception thrown by defineClass method", e.getCause());
        }
    }
}
