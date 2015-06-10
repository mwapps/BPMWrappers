package com.manriqueweb.droolswrapper.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.StatefulSession;
import org.drools.compiler.DroolsParserException;
import org.drools.compiler.PackageBuilder;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;

public class DroolswrapperHelperImp implements DroolsWrapperHelper {
	private static Logger logger = LogManager.getLogger(DroolswrapperHelperImp.class);
	private static DroolswrapperHelperImp instance = null;

	/**
	 * 
	 */
	private DroolswrapperHelperImp() {
	}

	public static synchronized DroolswrapperHelperImp getInstance() {
		if (instance==null){
			instance = new DroolswrapperHelperImp();
		}
		
		return instance;
	}

	private void stackTraceToString(Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.getMessage());
        sb.append(" \n ");
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString());
            sb.append(" \n ");
        }
		logger.error("error: \n ".concat(sb.toString()));
    }

	public String getSpreadsheetCompiler(String pathExcelFile) {
		if(pathExcelFile==null) return null;
		
		String response = null;
		InputStream excelFile = getSpreadsheetStream(pathExcelFile);
		if(excelFile!=null){
			response = getSpreadsheetCompiler(excelFile);
		}
		return response;
	}

	public String getSpreadsheetCompiler(InputStream inputStreamExcelFile) {
		String response = null;
		if(inputStreamExcelFile!=null){
	    	try {
		        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
		        response = compiler.compile(inputStreamExcelFile, InputType.XLS);
			} catch (Exception eException) {
				stackTraceToString(eException);
			}
		}
		return response;
	}

	public RuleBase buildRuleBase(String drl) {
		if(drl==null) return null;
		RuleBase ruleBase = null;
		
        try {
            PackageBuilder builder = new PackageBuilder();
			builder.addPackageFromDrl(new StringReader(drl));
	        ruleBase = RuleBaseFactory.newRuleBase();
	        ruleBase.addPackage(builder.getPackage());
		} catch (DroolsParserException exceptionOne) {
			stackTraceToString(exceptionOne);
			return null;
		} catch (IOException exceptionTwo) {
			stackTraceToString(exceptionTwo);
			return null;
		} catch (Exception eEexception) {
			stackTraceToString(eEexception);
			return null;
		}
        return ruleBase;
	}

	public RuleBase buildRuleBase(InputStream inputStreamExcelFile) {
        return buildRuleBase(getSpreadsheetCompiler(inputStreamExcelFile));
	}

	public StatefulSession getStatefulSession(InputStream inputStreamExcelFile) {
		// TODO Auto-generated method stub
		return null;
	}

	public StatefulSession getStatefulSession(String drl) {
		// TODO Auto-generated method stub
		return null;
	}

	public StatefulSession getStatefulSession(RuleBase ruleBase) {
		// TODO Auto-generated method stub
		return null;
	}

    private InputStream getSpreadsheetStream(String pathExcelFile){
    	InputStream response = null; 
    	try {
			response = new FileInputStream(new File(pathExcelFile));
		} catch (FileNotFoundException eFileNotFoundException) {
			stackTraceToString(eFileNotFoundException);
		}
        return response;
    }
}
