package com.qyk.Jupyter.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigurationFactory
{
    private static final Logger logger = Logger.getLogger(ConfigurationFactory.class);

    private static volatile Map<String, ConfigurationFactory> configs = new HashMap<String, ConfigurationFactory>();

    public static final String DEFAULT_CONFIGURATION_FILE = "sys.properties";

    private PropertiesReader propReader;

    private ConfigurationFactory(String fileName) {
        propReader = new PropertiesReader(fileName);
    }

    /**
     * @param propName
     * @return
     */
    public String getValue(String propName) {
        return propReader.getProperty(propName);
    }

    /*
     * Register default factory
     */
    static {
        registerFactory(DEFAULT_CONFIGURATION_FILE, new ConfigurationFactory(DEFAULT_CONFIGURATION_FILE));
    }

    /**
     * Get default configuration
     *
     * @return
     */
    public static final ConfigurationFactory getInstance() {
        return configs.get(DEFAULT_CONFIGURATION_FILE);
    }

    /*
     * Register factory
     */
    private static synchronized void registerFactory(String name, ConfigurationFactory factory) {
        if (name != null && factory != null) {
            logger.info("Register configuration file " + name);
            configs.put(name, factory);
        }

    }

    private static class PropertiesReader {
        private static final Logger logger = Logger.getLogger(PropertiesReader.class);

        Properties prop = new Properties();

        /**
         * This constructor will try to read the property file
         * from CLASSPATH in below order.
         * 1.Read by the ClassLoader which loads this class - PropertiesReader
         * 2.Read by the current thread context CLASSLOADER
         * 3.Read by the System ClassLoader
         *
         * @param propertyFile, fullPath of property file to be read
         */
        public PropertiesReader(String propertyFile) {

            loadPropertyFile(propertyFile);

        }


        private final void loadPropertyFile(String propertyFile) {
            if (propertyFile == null || "".equals(propertyFile)) {
                throw new IllegalArgumentException("PropertyFile name can not be NULL or blank.");
            }

            logger.debug("Try to loading property file " + propertyFile);

            InputStream in = getClass().getClassLoader().getResourceAsStream(propertyFile);

            if (in == null) {
                logger.info("Load property file using PropertiesReader ClassLoader failed. Try to use ThreadContext ClassLoader to load the property file.");

                ClassLoader threadCL = Thread.currentThread().getContextClassLoader();
                if (threadCL != null) {
                    in = threadCL.getResourceAsStream(propertyFile);
                }

                if (in == null) {
                    logger.info("Load property file using ThreadContext Classloader failed. Try to use System ClassLoader to load the property file.");
                    in = ClassLoader.getSystemClassLoader().getResourceAsStream(propertyFile);

                    if (in == null) {
                        throw new RuntimeException("Can not load Project property file " + propertyFile);
                    } else {
                        logger.info("Property file loaded from " + ClassLoader.getSystemClassLoader().getResource(propertyFile));
                    }
                } else {
                    logger.info("Property file loaded from " + threadCL.getResource(propertyFile));
                }


            } else {
                logger.info("Property file loaded from " + getClass().getClassLoader().getResource(propertyFile));
            }

            try {
                prop.load(in);

            } catch (IOException e) {
                throw new RuntimeException("Error occurs while loading file into properties " + e.getMessage());
            }
        }


        /**
         * Get property value
         *
         * @param key
         * @return
         */
        public String getProperty(String key) {
            if (key == null) {
                return null;
            } else {
                return prop.getProperty(key);
            }
        }

    }
}
