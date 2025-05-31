package com.njuse.seecjvm.instructions.comparison;

import com.njuse.seecjvm.instructions.base.NoOperandsInstruction;
import com.njuse.seecjvm.runtime.OperandStack;
import com.njuse.seecjvm.runtime.StackFrame;

public class DCMPL extends NoOperandsInstruction {

    /**
     * TODO：实现这条指令
     */
    @Override
    public void execute(StackFrame frame) {
        OperandStack stack = frame.getOperandStack();
        double num1 = stack.popDouble();
        double num2 = stack.popDouble();
        if(Double.isNaN(num2) || Double.isNaN(num1)){
            frame.getOperandStack().pushInt(-1);
        }else {
            frame.getOperandStack().pushInt(Double.compare(num2, num1));
        }
    }
}
