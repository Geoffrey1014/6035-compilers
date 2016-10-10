package edu.mit.compilers.ir;

import java.util.*;

public class IrMethodDecl extends IrMemberDecl {
    private final IrType returnType;
    private final List<IrMethodDeclParamDecl> paramsList;
    private final IrCodeBlock methodBody;

    public IrMethodDecl(IrType returnType, List<IrMethodDeclParamDecl> paramsList,
                       IrCodeBlock methodBody, IrIdent name, int lineNumber, int colNumber) {
        super(name, lineNumber, colNumber);
        this.returnType = returnType;
        this.paramsList = paramsList;
        this.methodBody = methodBody;
    }
}