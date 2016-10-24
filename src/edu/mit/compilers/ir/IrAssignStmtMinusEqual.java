package edu.mit.compilers.ir;

import edu.mit.compilers.ScopeStack;

/**
 * Created by devinmorgan on 10/5/16.
 */
public class IrAssignStmtMinusEqual extends IrAssignStmt {
    private final IrExpr decrementBy;

    public IrAssignStmtMinusEqual(IrLocation storeLocation, IrExpr decrementBy) {
        super(storeLocation);
        this.decrementBy = decrementBy;
    }

    @Override
    public String semanticCheck(ScopeStack scopeStack) {
        String errorMessage = "";

        // 1) verify that the storeLocation is semantically correct
        errorMessage += this.getStoreLocation().semanticCheck(scopeStack);

        if (this.getStoreLocation() instanceof IrLocationVar) {

            // 2) check to make sure the var isn't a lone array var
            if (scopeStack.checkIfSymbolExistsAtAnyScope(this.getStoreLocation().getLocationName().getValue())) {
                Ir object = scopeStack.getSymbol(this.getStoreLocation().getLocationName().getValue());
                if (object instanceof IrFieldDeclArray) {
                    errorMessage += "Can't use -= on the array itself" +
                            " line: " + this.getLineNumber() + " col: " + this.getColNumber() + "\n";
                }
            }
        }

        // 3) verify that the expr is semantically correct
        errorMessage += this.decrementBy.semanticCheck(scopeStack);

        // 4) make sure that the IrExpr and IrLocation are IrTypeInt
        if (!((this.decrementBy.getExpressionType() instanceof IrTypeInt) &&
                this.getStoreLocation().getExpressionType() instanceof IrTypeInt)) {
            errorMessage += "The variable and expression of -= must be of type int" +
                    " line: " + this.getLineNumber() + " col: " +this.getColNumber() + "\n";
        }

        return errorMessage;
    }
}
