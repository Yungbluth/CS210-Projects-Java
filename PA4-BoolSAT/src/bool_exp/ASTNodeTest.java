package bool_exp;

import org.junit.Test;

import junit.framework.Assert;

/**
 * 
 * AUTHOR: Matthew Yungbluth
 * File: ASTNodeTest.java
 * ASSIGNMENT: Programming Assignment 4 - BoolSat
 * COURSE: CSc 210; Section C; Spring 2019
 * PURPOSE: This program provides a junit test for ASTNode.java
 *
 */
public class ASTNodeTest {
    /**
     * Purpose: Tests for ASTNodes with null children
     */
    @Test
    public void testNullChildren() {
        ASTNode testNull = ASTNode.createNandNode(null, null);
        Assert.assertNull(testNull.child1);
        Assert.assertNull(testNull.child2);
    }

    /**
     * Purpose: Tests for ASTNodes with ASTNodes as children
     */
    @Test
    public void ASTChildren() {
        ASTNode testChildOne = ASTNode.createNandNode(null, null);
        ASTNode testChildTwo = ASTNode.createNandNode(null, null);
        ASTNode testParent = ASTNode.createNandNode(testChildOne, testChildTwo);
        Assert.assertSame(testChildOne, testParent.child1);
        Assert.assertSame(testChildTwo, testParent.child2);
    }

    /**
     * Purpose: Tests the functions isNand and isId
     */
    @Test
    public void validTypes() {
        ASTNode isNandTestValid = ASTNode.createNandNode(null, null);
        ASTNode isNandTestFail = ASTNode.createIdNode(null);
        ASTNode isIdValid = ASTNode.createIdNode(null);
        ASTNode isIdFail = ASTNode.createNandNode(null, null);
        Assert.assertTrue(isNandTestValid.isNand());
        Assert.assertFalse(isNandTestFail.isNand());
        Assert.assertTrue(isIdValid.isId());
        Assert.assertFalse(isIdFail.isId());
    }

    /**
     * Purpose: Tests the getId function
     */
    @Test
    public void IdTest() {
        ASTNode IdTest = ASTNode.createIdNode("HI");
        Assert.assertEquals(IdTest.getId(), "HI");
    }

}
