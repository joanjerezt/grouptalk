package edu.upc.eetac.dsa.grouptalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.grouptalk.BeeterRootAPIResource;
import edu.upc.eetac.dsa.grouptalk.GroupResource;
import edu.upc.eetac.dsa.grouptalk.LoginResource;
import edu.upc.eetac.dsa.grouptalk.UserResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by juan on 30/10/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Group {
    @InjectLinks({
            @InjectLink(resource = BeeterRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Beeter Root API"),
            @InjectLink(resource = GroupResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-groups", title = "Current groups"),
            @InjectLink(resource = GroupResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-groups", title = "Create group", type = MediaType.APPLICATION_FORM_URLENCODED),
            @InjectLink(resource = GroupResource.class, method = "getGroup", style = InjectLink.Style.ABSOLUTE, rel = "self group", title = "Group", bindings = @Binding(name = "id", value = "${instance.id}")),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "user-profile", title = "User profile", bindings = @Binding(name = "id", value = "${instance.userid}")),
            @InjectLink(resource = GroupResource.class, method = "getGroups", style = InjectLink.Style.ABSOLUTE, rel = "next", title = "Newer groups", bindings = {@Binding(name = "timestamp", value = "${instance.creationTimestamp}"), @Binding(name = "before", value = "false")}),
            @InjectLink(resource = GroupResource.class, method = "getGroups", style = InjectLink.Style.ABSOLUTE, rel = "previous", title = "Older groups", bindings = {@Binding(name = "timestamp", value = "${instance.creationTimestamp}"), @Binding(name = "before", value = "true")}),
    })
    private List<Link> links;
    private String id;
    private String groupname;
    private long creationTimestamp;
    private long lastModified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
