package edu.upc.eetac.dsa.grouptalk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.upc.eetac.dsa.grouptalk.*;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by juan on 28/09/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @InjectLinks({
            @InjectLink(resource = BeeterRootAPIResource.class, style = InjectLink.Style.ABSOLUTE, rel = "home", title = "Beeter Root API"),
            @InjectLink(resource = StingResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-stings", title = "Current stings", type= BeeterMediaType.BEETER_STING_COLLECTION),
            @InjectLink(resource = StingResource.class, style = InjectLink.Style.ABSOLUTE, rel = "current-groups", title = "Current groups", type= BeeterMediaType.BEETER_GROUP_COLLECTION),
            @InjectLink(resource = LoginResource.class, style = InjectLink.Style.ABSOLUTE, rel = "logout", title = "Logout"),
            @InjectLink(resource = StingResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-sting", title = "Create sting", type=BeeterMediaType.BEETER_STING),
            @InjectLink(resource = StingResource.class, style = InjectLink.Style.ABSOLUTE, rel = "create-group", title = "Create group", type=BeeterMediaType.BEETER_GROUP),
            @InjectLink(resource = UserResource.class, method = "getUser", style = InjectLink.Style.ABSOLUTE, rel = "self user-profile", title = "User profile", type= BeeterMediaType.BEETER_USER, bindings = @Binding(name = "id", value = "${instance.id}"))
    })

        private String id;
        private String loginid;
        private String email;
        private String fullname;
        private List<Link> links;

        public List<Link> getLinks() {
            return links;
        }

        public void setLinks(List<Link> links) {
            this.links = links;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLoginid() {
            return loginid;
        }

        public void setLoginid(String loginid) {
            this.loginid = loginid;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }
    }
