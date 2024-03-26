package com.YusufFakhreddin.TicketingSystem.team;

import com.YusufFakhreddin.TicketingSystem.team.enums.TeamName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {
    private final TeamRepo teamRepo;

    @Override
    public Team findTeamByName(TeamName name) {
        return teamRepo.findByName(name);
    }
}
