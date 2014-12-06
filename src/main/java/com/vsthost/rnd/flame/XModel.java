package com.vsthost.rnd.flame;

import ch.qos.logback.classic.Logger;
import com.vsthost.rnd.flame.xsd.XSDModels;
import com.vsthost.rnd.flame.xsd.XSDXmodel;
import org.slf4j.LoggerFactory;

import javax.activation.FileDataSource;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vst on 6/12/14.
 */
public class XModel {
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
     * Defines nested models:
     */
    public List<XModel> models = new ArrayList<XModel>();

    /**
     * Returns the data of the XModel.
     *
     * @return The data of the XModel.
     */
    public XSDXmodel getData() {
        return data;
    }

    /**
     * Sets the data of the XModel.
     *
     * @param data The data of the XModel.
     */
    public void setData(XSDXmodel data) {
        this.data = data;
    }

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

    @Override
    public String toString() {
        return String.format("%s (%s)", this.getData().getName(), this.getPath());
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

        // Create the xmodel instance.
        XModel xmodel = new XModel();

        // Set the path:
        xmodel.setPath(new File(baseDirectory + "/" + filepath).getAbsolutePath());

        // Set the data:
        xmodel.setData((XSDXmodel) JAXBContext
                .newInstance("com.vsthost.rnd.flame.xsd")
                .createUnmarshaller()
                .unmarshal(new StreamSource(new File(baseDirectory, filepath))));

        // Get nested models:
        if (xmodel.getData().getModels() != null) {
            for (XSDModels.XSDModel i : xmodel.getData().getModels().getModel()) {
                xmodel.models.add(XModel.parseXMML(baseDirectory, i.getFile()));
            }
        }

        // Done, return:
        return xmodel;
    }
}
