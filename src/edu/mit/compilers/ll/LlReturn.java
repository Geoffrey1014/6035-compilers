package edu.mit.compilers.ll;

import edu.mit.compilers.AssemblyBuilder;
import edu.mit.compilers.LlSymbolTable;
import edu.mit.compilers.StackFrame;

/**
 * Created by devinmorgan on 11/18/16.
 */
public class LlReturn extends LlStatement {

    private final LlComponent returnValue;

    public LlReturn(LlComponent returnValue) {
        this.returnValue = returnValue;
    }

    public LlComponent getReturnValue() {
        return this.returnValue;
    }

    @Override
    public String toString() {
        if (this.returnValue == null) {
            return "return ";
        }
        return "return " + this.returnValue.toString();
    }

    public String generateCode(AssemblyBuilder builder, StackFrame frame, LlSymbolTable symbolTable){

        if(this.returnValue == null){
            if(builder.isLastReturn){
                builder.calleeRestore(builder.getAllAllocatedRegs(), frame);
                builder.addLinef("leave","");
                builder.addLinef(".cfi_def_cfa","7, 8");
                builder.addLinef("ret", "");
                builder.addLinef(".cfi_endproc", "");
                builder.addLine();
                builder.isLastReturn = false;
                builder.hasReturned = true;
                return "%rax";
            }
            builder.calleeRestore(builder.getAllAllocatedRegs(), frame);
            builder.addLinef("leave","");
            builder.addLinef("ret", "");
            builder.addLine();
        }

        else{
            String returnAdd = returnValue.generateCode(builder, frame, symbolTable);
            builder.addLinef("movq", returnAdd + ", %rax");
            builder.calleeRestore(builder.getAllAllocatedRegs(), frame);
            if(builder.isLastReturn){
                builder.addLinef("leave","");
                builder.addLinef(".cfi_def_cfa","7, 8");
                builder.addLinef("ret", "");
                builder.addLinef(".cfi_endproc", "");
                builder.addLine();
                builder.isLastReturn = false;
                builder.hasReturned = true;
                return "%rax";
            }
            builder.calleeRestore(builder.getAllAllocatedRegs(), frame);
            builder.addLinef("leave","");
            builder.addLinef("ret", "");
            builder.addLine();
        }

        return "%rax";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LlReturn) {
            LlReturn that = (LlReturn) obj;

            if (this.returnValue == null || that.returnValue == null) {
                return this.returnValue == null && that.returnValue == null;
            }
            return this.returnValue.equals(that.returnValue);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.returnValue.hashCode();
    }
}
