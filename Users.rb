require 'dbi'

def get_user(uid)
    user = null
    begin
        # connect to the MySQL server
        dbh = DBI.connect("DBI:Mysql:TESTDB:localhost", 
                               "testuser", "test123")
        sth = dbh.prepare("SELECT * FROM User WHERE id = ?")
        sth.execute(uid)
        user = sth.fetch[0]
        sth.finish
    rescue DBI::DatabaseError => e
        puts "An error occurred"
        puts "Error code:    #{e.err}"
        puts "Error message: #{e.errstr}"
    ensure
        # disconnect from server
        dbh.disconnect if dbh
    end
    return user
end
