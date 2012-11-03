package org.apache.flex.compiler.internal.tree.mxml;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.apache.flex.compiler.css.ICSSDocument;
import org.apache.flex.compiler.css.ICSSRule;
import org.apache.flex.compiler.tree.ASTNodeID;
import org.apache.flex.compiler.tree.mxml.IMXMLFileNode;
import org.apache.flex.compiler.tree.mxml.IMXMLStyleNode;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

/**
 * JUnit tests for {@link MXMLStyleNode}.
 * 
 * @author Gordon Smith
 */
public class MXMLStyleNodeTests extends MXMLNodeBaseTests
{
	private static String PREFIX =
	    "<d:Sprite xmlns:fx='http://ns.adobe.com/mxml/2009' xmlns:d='flash.display.*'>\n\t";
				
	private static String POSTFIX =
		"\n</d:Sprite>";
	    
	private static String EOL = "\n\t";
	
    @Override
    protected IMXMLFileNode getMXMLFileNode(String code)
    {
    	return super.getMXMLFileNode(PREFIX + code + POSTFIX);
    }
    
	private IMXMLStyleNode getMXMLStyleNode(String code)
	{
		IMXMLFileNode fileNode = getMXMLFileNode(code);
		IMXMLStyleNode node = (IMXMLStyleNode)findFirstDescendantOfType(fileNode, IMXMLStyleNode.class);
		assertThat("getNodeID", node.getNodeID(), is(ASTNodeID.MXMLStyleID));
		assertThat("getName", node.getName(), is("Style"));
		return node;
	}
	
	@Test
	public void MXMLStyleNode_empty1()
	{
		String code = "<fx:Style/>";
		IMXMLStyleNode node = getMXMLStyleNode(code);
		assertThat("getChildCount", node.getChildCount(), is(0));
	}
	
	@Test
	public void MXMLStyleNode_empty2()
	{
		String code = "<fx:Style></fx:Style>";
		IMXMLStyleNode node = getMXMLStyleNode(code);
		assertThat("getChildCount", node.getChildCount(), is(0));
	}
	
	@Test
	public void MXMLStyleNode_empty3()
	{
		String code = "<fx:Style/> \t\r\n<fx:Style/>";
		IMXMLStyleNode node = getMXMLStyleNode(code);
		assertThat("getChildCount", node.getChildCount(), is(0));
	}
	
	@Test
	public void MXMLStyleNode_two_rules()
	{
		String code =
			"<fx:Style>" + EOL +
			"    Button { font-size: 20; color: red }" + EOL +
			"    CheckBox { font-size: 16 }" + EOL +
			"</fx:Style>";
		IMXMLStyleNode node = getMXMLStyleNode(code);
		assertThat("getChildCount", node.getChildCount(), is(0));
		ICSSDocument css = node.getCSSDocument(null);
		ImmutableList<ICSSRule> rules = css.getRules();
		assertThat("rules", rules.size(), is(2));
		assertThat("rule 0 name", rules.get(0).getSelectorGroup().get(0).getElementName(), is("Button"));
		assertThat("rule 1 name", rules.get(1).getSelectorGroup().get(0).getElementName(), is("CheckBox"));
	}
}