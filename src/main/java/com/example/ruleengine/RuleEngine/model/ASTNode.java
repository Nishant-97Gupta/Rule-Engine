package com.example.ruleengine.RuleEngine.model;

public class ASTNode {
	
	private String type;
	private ASTNode left;
	private ASTNode right;
	private String value;
	
	
	// Constructor for operand node (leaf node)
    public ASTNode(String type, String value) {
        this.type = type;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    // Constructor for operator node (internal node)
    public ASTNode(String type, ASTNode left, ASTNode right) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.value = null;
    }


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public ASTNode getLeft() {
		return left;
	}


	public void setLeft(ASTNode left) {
		this.left = left;
	}


	public ASTNode getRight() {
		return right;
	}


	public void setRight(ASTNode right) {
		this.right = right;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}

	 @Override
	    public String toString() {
	        if ("operand".equals(type)) {
	            return value;
	        } else {
	            return "(" + left.toString() + " " + type + " " + right.toString() + ")";
	        }
	    }
	}