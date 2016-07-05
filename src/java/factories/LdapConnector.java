package factories;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;


/**
 * @author jose
 */

public class LdapConnector {

    private static DirContext ctx = null;
    private static Properties props = null;
    private static final String SUFFIX = "dc=StopBadFiles,dc=com";
    private static final String olcRootDN = "cn=admin," + SUFFIX;
    private static final String olcRootPW = "departamento";
    private static final String URL = "10.10.2.70";
    
    public LdapConnector() {
        
        props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        props.put(Context.PROVIDER_URL, "ldap://" + URL + ":389/" + SUFFIX);
        props.put(Context.SECURITY_AUTHENTICATION, "simple");
        props.put(Context.SECURITY_PRINCIPAL, olcRootDN);
        props.put(Context.SECURITY_CREDENTIALS, olcRootPW);
        try {
            ctx = new InitialDirContext(props);
        } catch (Exception e) {

        }
    }

    public static LdapConnector getInstance() {
        return LdapConnector.LdapConnectorHolder.INSTANCE;
    }

    private static String getUserDn(String username) {
        return "cn=" + username + ",ou=Users," + SUFFIX;
    }

   
    public boolean login(String username, String password) {
        try {
            Properties tempProp = new Properties();
            tempProp.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            tempProp.put(Context.PROVIDER_URL, "ldap://" + URL + ":389/" + SUFFIX);
            tempProp.put(Context.SECURITY_AUTHENTICATION, "simple");
            tempProp.put(Context.SECURITY_PRINCIPAL, getUserDn(username));
            tempProp.put(Context.SECURITY_CREDENTIALS, password);
            DirContext tempCtx = new InitialDirContext(tempProp);
            tempCtx.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUidNumber(String uid) {
        String attrsFilters[] = {"uidNumber"};
        String filter = "(&(uid="+uid+"))";
        String base = "ou=Users";
        try {
            SearchControls searchControls = new SearchControls();
            searchControls.setReturningAttributes(attrsFilters);
            searchControls.setSearchScope(SearchControls.ONELEVEL_SCOPE);
            NamingEnumeration<SearchResult> results = ctx.search(base, filter, searchControls);
            while (results.hasMoreElements()) {
                SearchResult result = (SearchResult) results.next();
                Attributes attrs = result.getAttributes();
                Attribute uidNumber = attrs.get("uidNumber");
                return (String) uidNumber.get();
            }
        } catch (Exception e){
        }
        return null;
    }

    private static class LdapConnectorHolder {
        private static final LdapConnector INSTANCE = new LdapConnector();
    }

}
