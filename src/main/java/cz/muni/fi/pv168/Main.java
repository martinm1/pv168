/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pv168;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.derby.jdbc.EmbeddedDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.util.List;
import org.apache.derby.jdbc.ClientDriver;

/**
 *
 * @author Boris
 */
public class Main {
    final static Logger log = LoggerFactory.getLogger(Main.class);
    
    public static DataSource createMemoryDatabase() {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName(ClientDriver.class.getName());
        bds.setUrl("jdbc:derby://localhost:1527/MissionDB");
        new ResourceDatabasePopulator(
                new ClassPathResource("schema-javadb.sql"),
                new ClassPathResource("test-data.sql"))
                .execute(bds);
        return bds;
    
    }
    
    public static void main(String[] args) {

        log.info("zaciname");

        DataSource dataSource = createMemoryDatabase();
        MissionManager missionManager = new MissionManagerImpl();
        missionManager.setDataSource(dataSource);

        List<Mission> allMissions = missionManager.findAllMissions();
        System.out.println("allMissions = " + allMissions);
        
        AgentManager agentManager = new AgentManagerImpl();
        missionManager.setDataSource(dataSource);

        List<Agent> allAgents = agentManager.findAllAgents();
        System.out.println("allAgents = " + allAgents);

    }
    
}
