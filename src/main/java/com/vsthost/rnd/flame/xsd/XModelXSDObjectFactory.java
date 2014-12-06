package com.vsthost.rnd.flame.xsd;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * Created by vst on 6/12/14.
 */
@XmlRegistry
public class XModelXSDObjectFactory extends ObjectFactory {
    public XModel createXSDXmodel() {
        return new XModel();
    }
}