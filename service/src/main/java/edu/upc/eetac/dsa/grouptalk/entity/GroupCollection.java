package edu.upc.eetac.dsa.grouptalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.grouptalk.BeeterMediaType;
import edu.upc.eetac.dsa.grouptalk.BeeterRootAPIResource;
import edu.upc.eetac.dsa.grouptalk.GroupResource;
import edu.upc.eetac.dsa.grouptalk.LoginResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan on 30/10/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupCollection
{
    @InjectLinks({
            @InjectLink(resource = BeeterRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Beeter Root API"),
            @InjectLink(resource = GroupResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-groups", title = "Current groups", type= BeeterMediaType.BEETER_GROUP_COLLECTION),
            @InjectLink(resource = GroupResource.class, method = "getGroups", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Newer groups", type= BeeterMediaType.BEETER_GROUP_COLLECTION, bindings = {@Binding(name = "timestamp", value = "${instance.newestTimestamp}"), @Binding(name = "before", value = "false")}),
            @InjectLink(resource = GroupResource.class, method = "getGroups", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Older groups", type= BeeterMediaType.BEETER_GROUP_COLLECTION, bindings = {@Binding(name = "timestamp", value = "${instance.oldestTimestamp}"), @Binding(name = "before", value = "true")}),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout")
    })

    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Group> groups = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public long getNewestTimestamp() {
        return newestTimestamp;
    }

    public void setNewestTimestamp(long newestTimestamp) {
        this.newestTimestamp = newestTimestamp;
    }

    public long getOldestTimestamp() {
        return oldestTimestamp;
    }

    public void setOldestTimestamp(long oldestTimestamp) {
        this.oldestTimestamp = oldestTimestamp;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
