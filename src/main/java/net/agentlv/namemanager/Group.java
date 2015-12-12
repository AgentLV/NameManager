package net.agentlv.namemanager;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author AgentLV
 */
@NoArgsConstructor
public class Group {

    @Getter @Setter private int id = 0;
    @Getter private Set<String> players = new HashSet<>();

    public Group(int id) {
        this.id = id;
    }


}
