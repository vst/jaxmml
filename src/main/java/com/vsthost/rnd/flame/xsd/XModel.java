package com.vsthost.rnd.flame.xsd;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vst on 6/12/14.
 */
public class XModel extends XSDXmodel {
    /**
     * Defines the logger for the class.
     */
    private static Logger Log = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    /**
     * Defines the model data.
     */
    private XSDXmodel data;

    /**
     * Defines the path of the XMML file.
     */
    private String path;

    /**
     * Defines nested nestedModels:
     */
    private List<XModel> nestedModels;

    /**
     * Returns the path of the XMML file.
     *
     * @return The path of the XMML file.
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the path of the XMML file.
     *
     * @param path The path of the XMML file.
     */
    public void setPath(String path) {
        this.path = path;
    }

    private void parseNestedModels () throws FileNotFoundException, JAXBException {
        // Initialise the nested nestedModels:
        this.nestedModels = new ArrayList<XModel>();

        // Read and add nestedModels:
        if (this.getModels() != null) {
            for (XSDModels.XSDModel i : this.getModels().getModel()) {
                this.nestedModels.add(XModel.parseXMML(new File(this.getPath()).getParent(), i.getFile()));
            }
        }
    }

    /**
     * Returns nested models.
     *
     * @return Nested models.
     */
    public List<XModel> getNestedModels () throws FileNotFoundException, JAXBException {
        // Check if we have already parsed:
        if (this.nestedModels != null) {
            return this.nestedModels;
        }

        // Parse nestedModels and populate our nested nestedModels:
        this.parseNestedModels();

        // Return nested nestedModels:
        return this.nestedModels;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", this.getName(), this.getPath());
    }

    /**
     * Parses and returns an XModel.
     *
     * @param filepath File path to the XMML file.
     * @return An XModel instance.
     */
    public static XModel parseXMML(String filepath) throws FileNotFoundException, JAXBException {
        File file = new File(filepath);
        return XModel.parseXMML(file.getParent(), file.getName());
    }

    /**
     * Parses and returns an XModel.
     *
     * @param baseDirectory the base directory of the file.
     * @param filepath File path to the XMML file.
     * @return An XModel instance.
     */
    public static XModel parseXMML(String baseDirectory, String filepath) throws FileNotFoundException, JAXBException {
        Log.trace("Parsing fileapath '" + filepath + "'");

        // Create the JAXB Context:
        JAXBContext context = JAXBContext.newInstance(XModelXSDObjectFactory.class);

        // Get the unmarshaller:
        Unmarshaller unmarshaller = context.createUnmarshaller();

        // We want to use our own object factory instead of the XJC generated one:
        unmarshaller.setProperty("com.sun.xml.internal.bind.ObjectFactory", new XModelXSDObjectFactory());

        // Get the XModel:
        XModel xmodel = (XModel) unmarshaller.unmarshal(new StreamSource(new File(baseDirectory, filepath)));

        // Set the path:
        xmodel.setPath(new File(baseDirectory + "/" + filepath).getAbsolutePath());

        // Return the xmodel:
        return xmodel;
    }
}
