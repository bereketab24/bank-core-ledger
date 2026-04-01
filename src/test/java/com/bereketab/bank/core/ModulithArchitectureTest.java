package com.bereketab.bank.core;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

public class ModulithArchitectureTest {
    ApplicationModules modules = ApplicationModules.of(CoreApplication.class);

    @Test
    void verifyModulithArchitecture(){
        modules.verify();
    }

    @Test
    void generateUMLDocument(){
        new org.springframework.modulith.docs.Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }

}
