package com.jrasp.agent.module.jsp.algorithm;

import com.jrasp.agent.api.Module;
import com.jrasp.agent.api.ModuleLifecycleAdapter;
import com.jrasp.agent.api.RaspConfig;
import com.jrasp.agent.api.algorithm.AlgorithmManager;
import com.jrasp.agent.api.annotation.Information;
import com.jrasp.agent.api.annotation.RaspResource;
import com.jrasp.agent.api.log.RaspLog;
import com.jrasp.agent.module.jsp.algorithm.impl.JspCompileAlgorithm;
import com.jrasp.agent.module.jsp.algorithm.impl.JstlImportAlgorithm;
import org.kohsuke.MetaInfServices;

import java.util.Map;

@MetaInfServices(Module.class)
@Information(id = "jsp-algorithm", author = "yhlong")
public class JspAlgorithmModule extends ModuleLifecycleAdapter implements Module {

    @RaspResource
    private RaspLog logger;

    @RaspResource
    private RaspConfig raspConfig;

    @RaspResource
    private AlgorithmManager algorithmManager;

    @RaspResource
    private String metaInfo;

    private volatile JspCompileAlgorithm jspCompileAlgorithm;

    private volatile JstlImportAlgorithm jstlImportAlgorithm;

    @Override
    public boolean update(Map<String, String> configMaps) {
        this.jspCompileAlgorithm = new JspCompileAlgorithm(configMaps, raspConfig, logger, metaInfo);
        this.jstlImportAlgorithm = new JstlImportAlgorithm(configMaps, raspConfig, logger, metaInfo);
        algorithmManager.register(jspCompileAlgorithm, jstlImportAlgorithm);
        return true;
    }

    @Override
    public void loadCompleted() {
        this.jspCompileAlgorithm = new JspCompileAlgorithm(logger);
        this.jstlImportAlgorithm = new JstlImportAlgorithm(logger);
        algorithmManager.register(jspCompileAlgorithm, jstlImportAlgorithm);
    }
}
