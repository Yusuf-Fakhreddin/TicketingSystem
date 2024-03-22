package com.YusufFakhreddin.ICDTicketingSystem.service;

import com.YusufFakhreddin.ICDTicketingSystem.entity.Team;
import com.YusufFakhreddin.ICDTicketingSystem.enums.TeamName;


public interface TeamService {

//    findTeamByName
    Team findTeamByName(TeamName name);
}
