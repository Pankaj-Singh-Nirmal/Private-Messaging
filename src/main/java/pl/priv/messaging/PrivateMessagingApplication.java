package pl.priv.messaging;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class PrivateMessagingApplication implements CommandLineRunner
{
	private static final Logger logger = Logger.getLogger(PrivateMessagingApplication.class.getName());

    @Autowired
    JdbcTemplate jdbcTemplate;
	
	public static void main(String[] args) 
	{
		SpringApplication.run(PrivateMessagingApplication.class, args);
	}

	@Override
    public void run(String... args) 
	{
		logger.info("StartApplication...");
        runJDBC();
    }

    void runJDBC() 
    {
    	logger.info("Creating tables...");
    	
    	jdbcTemplate.execute
    	(
			"CREATE TABLE IF NOT EXISTS user_details" 
			   + "(" 
			   + "		user_id VARCHAR(50) NOT NULL," 
			   + "		first_name VARCHAR(50) NOT NULL," 
			   + "		last_name VARCHAR(50) NOT NULL," 
			   + "		password VARCHAR(70) NOT NULL," 
			   + "		PRIMARY KEY(user_id)" 
			   + ");"
    	);
    	
    	jdbcTemplate.execute
    	(
			"CREATE TABLE IF NOT EXISTS private_chat " 
			   + "(" 
			   + "		sl_no INT AUTO_INCREMENT," 
			   + "		sender_user_id VARCHAR(50)," 
			   + "		receiver_user_id VARCHAR(50),"
			   + "		message text,"
			   + "		timestamp timestamp,"
			   + "		message_status varchar(6) default 'Unread',"
			   + "		PRIMARY KEY(sl_no)" 
			   + ");"
    	);
    	
    	jdbcTemplate.execute
    	(
			"CREATE TABLE IF NOT EXISTS users " 
			   + "(" 
			   + "		username VARCHAR(50) NOT NULL, " 
			   + "		password VARCHAR(70) NOT NULL, " 
			   + "		enabled TINYINT NOT NULL DEFAULT 1, " 
			   + "  	PRIMARY KEY(username)" 
			   + ");"
    	);
    	
    	jdbcTemplate.execute
    	(
			"CREATE TABLE IF NOT EXISTS authorities " 
			   + "(" 
			   + "		username VARCHAR(50) NOT NULL, " 
			   + "		authority VARCHAR(50) NOT NULL, " 
			   + "  	CONSTRAINT FK_AUTHORITIES_USERS FOREIGN KEY(username) REFERENCES users(username)" 
			   + "		ON UPDATE CASCADE"
			   + ");"
    	);
    }
}
