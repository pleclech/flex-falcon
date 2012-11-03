package org.apache.flex.compiler.internal.tree.mxml;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.apache.flex.compiler.tree.ASTNodeID;
import org.apache.flex.compiler.tree.metadata.IEventTagNode;
import org.apache.flex.compiler.tree.metadata.IMetaTagNode;
import org.apache.flex.compiler.tree.mxml.IMXMLFileNode;
import org.apache.flex.compiler.tree.mxml.IMXMLMetadataNode;
import org.junit.Test;

/**
 * JUnit tests for {@link MXMLMetadataNode}.
 * 
 * @author Gordon Smith
 */
public class MXMLMetadataNodeTests extends MXMLNodeBaseTests
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
    
	private IMXMLMetadataNode getMXMLMetadataNode(String code)
	{
		IMXMLFileNode fileNode = getMXMLFileNode(code);
		IMXMLMetadataNode node = (IMXMLMetadataNode)findFirstDescendantOfType(fileNode, IMXMLMetadataNode.class);
		assertThat("getNodeID", node.getNodeID(), is(ASTNodeID.MXMLMetadataID));
		assertThat("getName", node.getName(), is("Metadata"));
		return node;
	}
	
	@Test
	public void MXMLMetadataNode_empty1()
	{
		String code = "<fx:Metadata/>";
		IMXMLMetadataNode node = getMXMLMetadataNode(code);
		assertThat("getChildCount", node.getChildCount(), is(0));
	}
	
	@Test
	public void MXMLMetadataNode_empty2()
	{
		String code = "<fx:Metadata></fx:Metadata>";
		IMXMLMetadataNode node = getMXMLMetadataNode(code);
		assertThat("getChildCount", node.getChildCount(), is(0));
	}
	
	@Test
	public void MXMLMetadataNode_empty3()
	{
		String code = "<fx:Metadata/> \t\r\n<fx:Metadata/>";
		IMXMLMetadataNode node = getMXMLMetadataNode(code);
		assertThat("getChildCount", node.getChildCount(), is(0));
	}
	
	@Test
	public void MXMLMetadataNode_two_events()
	{
		String code =
			"<fx:Metadata>" + EOL +
			"    [Event(name='mouseDown', type='mx.events.MouseEvent')]" + EOL +
			"    [Event(name='mouseUp', type='mx.events.MouseEvent')]" + EOL +
			"</fx:Metadata>";
		IMXMLMetadataNode node = getMXMLMetadataNode(code);
		assertThat("getChildCount", node.getChildCount(), is(2));
		IMetaTagNode[] metaTagNodes = node.getMetaTagNodes();
		assertThat("event 0", ((IEventTagNode)metaTagNodes[0]).getName(), is("mouseDown"));
		assertThat("event 1", ((IEventTagNode)metaTagNodes[1]).getName(), is("mouseUp"));
	}
}