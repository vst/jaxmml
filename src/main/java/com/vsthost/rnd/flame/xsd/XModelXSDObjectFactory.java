package com.vsthost.rnd.flame.xsd;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * Overrides the XSD generated ObjectFactory to extend xmodel element.
 *
 * @author Vehbi Sinan Tunalioglu
 */
@XmlRegistry
public class XModelXSDObjectFactory extends ObjectFactory {
    public XModel createXSDXmodel() {
        return new XModel();
    }
}