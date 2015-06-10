package com.manriqueweb.droolswrapper.helpers;

import java.io.InputStream;

import org.drools.RuleBase;
import org.drools.StatefulSession;

public interface DroolsWrapperHelper {
	public abstract String getSpreadsheetCompiler(String pathExcelFile);
	public abstract String getSpreadsheetCompiler(InputStream inputStreamExcelFile);

	public abstract RuleBase buildRuleBase(String drl);
	public abstract RuleBase buildRuleBase(InputStream inputStreamExcelFile);
	
	public abstract StatefulSession getStatefulSession(InputStream inputStreamExcelFile);
	public abstract StatefulSession getStatefulSession(String drl);
	public abstract StatefulSession getStatefulSession(RuleBase ruleBase);

}
