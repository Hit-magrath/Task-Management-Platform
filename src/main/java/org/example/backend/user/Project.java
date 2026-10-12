package org.example.backend.user;

import io.javalin.http.Task;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class Project {
    private final Person owner;
    private final String descripstion ;
    private final UUID id ;
    private final HashMap<UUID, JoinRequest> joinRequets;

    public Project(Person owner , String descripstion ){
        this.owner = owner;
        this.descripstion = descripstion;
        this.id = UUID.randomUUID();
        this.joinRequets = new HashMap<>();
    }

    public JoinRequest requestJoin(Person personToJoin){
        JoinRequest joinRequest = new JoinRequest(this,personToJoin);
        joinRequets.put(joinRequest.getId() , joinRequest);
        return joinRequest;
    }

    public Collection<JoinRequest> listOfJoinRequests() {
        return Collections.unmodifiableCollection(joinRequets.values());

    }


}
