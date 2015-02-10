package org.kagelabs.pkbasic;

/**
 * Directive tyoe enum
 * @author Patrick Kage
 *
 */
public enum DirectiveType {
	INVALID,
	BLANK,
	COMPARATION,
	LABEL,
	GOTO,
	ASSIGNMENT,
	TERMINATION,
	LOADEXTERNAL,
	CALLEXTERNAL,
	SUBROUTINEDEFINITION,
	SUBROUTINECALL,
	SHIELDEDDEFINITION,
	SHIELDEDCALL
}
