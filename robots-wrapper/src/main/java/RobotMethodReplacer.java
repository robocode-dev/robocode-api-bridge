import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.*;
import java.io.ByteArrayInputStream;

public class RobotMethodReplacer {

    /**
     * Transforms a robot class to replace "while(true)" with "while(getEnergy() >= 0)"
     * in the run() method.
     *
     * @param robotClass The loaded robot class to modify
     * @return The transformed class or null if no transformation was performed
     */
    public static Class<?> transformRobotClass(Class<?> robotClass) {
        try {
            // Get the bytecode of the class
            String className = robotClass.getName();
            byte[] classBytes = getClassBytes(robotClass);

            // Transform the bytecode
            byte[] transformedBytes = transformClassBytes(classBytes, className);

            // If no transformation was made, return null
            if (transformedBytes == null) {
                return null;
            }

            // Define the new class with the transformed bytecode
            return defineTransformedClass(className, transformedBytes, robotClass.getClassLoader());
        } catch (Exception e) {
            System.err.println("Error transforming robot class: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] getClassBytes(Class<?> clazz) throws Exception {
        String resourceName = clazz.getName().replace('.', '/') + ".class";
        try (ByteArrayInputStream bis = new ByteArrayInputStream(
                clazz.getClassLoader().getResourceAsStream(resourceName).readAllBytes())) {
            return bis.readAllBytes();
        }
    }

    private static byte[] transformClassBytes(byte[] classBytes, String className) {
        try {
            // Parse the class
            ClassParser parser = new ClassParser(new ByteArrayInputStream(classBytes), className);
            JavaClass javaClass = parser.parse();

            // Create a class generator from the parsed class
            ClassGen classGen = new ClassGen(javaClass);
            ConstantPoolGen cpg = classGen.getConstantPool();

            boolean modified = false;

            // Look for the run() method
            for (Method method : javaClass.getMethods()) {
                if (method.getName().equals("run") && method.getSignature().equals("()V")) {
                    // Create a method generator for the run method
                    MethodGen methodGen = new MethodGen(method, className, cpg);
                    InstructionList instructionList = methodGen.getInstructionList();
                    InstructionHandle[] handles = instructionList.getInstructionHandles();

                    // Look for while(true) pattern: ICONST_1 (true) followed by IF_ICMPEQ or IF_ICMPNE
                    for (int i = 0; i < handles.length - 1; i++) {
                        Instruction inst = handles[i].getInstruction();

                        if (inst instanceof ICONST && ((ICONST) inst).getValue().intValue() == 1) {
                            Instruction nextInst = handles[i + 1].getInstruction();
                            if (nextInst instanceof IfInstruction) {
                                // Replace ICONST_1 (true) with a call to getEnergy() >= 0
                                InstructionHandle ih = handles[i];
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
                                newInstructions.append(new IFLT(((BranchInstruction) nextInst).getTarget()));

                                // Replace the original instructions
                                try {
                                    instructionList.delete(ih, handles[i + 1]);
                                    instructionList.insert(ih, newInstructions);
                                    modified = true;
                                    break;
                                } catch (TargetLostException e) {
                                    // Handle any branch targets that were lost
                                    InstructionHandle[] targets = e.getTargets();
                                    for (InstructionHandle target : targets) {
                                        InstructionTargeter[] targeters = target.getTargeters();
                                        for (InstructionTargeter targeter : targeters) {
                                            targeter.updateTarget(target, ih);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (modified) {
                        // Update the method with the modified instruction list
                        methodGen.setMaxStack();
                        methodGen.setMaxLocals();
                        classGen.replaceMethod(method, methodGen.getMethod());
                        break;
                    }
                }
            }

            if (modified) {
                // Generate the modified class bytes
                return classGen.getJavaClass().getBytes();
            }
        } catch (Exception e) {
            System.err.println("Error during bytecode transformation: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    private static Class<?> defineTransformedClass(String className, byte[] classBytes, ClassLoader classLoader) {
        try {
            // Use reflection to access the defineClass method of ClassLoader
            java.lang.reflect.Method defineClassMethod = ClassLoader.class.getDeclaredMethod(
                    "defineClass", String.class, byte[].class, int.class, int.class);
            defineClassMethod.setAccessible(true);

            // Define the new class with the transformed bytecode
            return (Class<?>) defineClassMethod.invoke(
                    classLoader, className, classBytes, 0, classBytes.length);
        } catch (Exception e) {
            System.err.println("Error defining transformed class: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
