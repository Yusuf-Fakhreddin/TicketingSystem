package com.YusufFakhreddin.ICDTicketingSystem.team;

import com.YusufFakhreddin.ICDTicketingSystem.team.enums.TeamName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepo extends JpaRepository<Team, String> {
    Team findByName(TeamName name);
}