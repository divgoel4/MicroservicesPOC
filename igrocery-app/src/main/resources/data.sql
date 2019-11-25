DELETE FROM User;

insert into User(id, email, first_name, last_name, active, is_Locked, is_Expired, is_Enabled) VALUES
(1, 'shrishailhundekari.wit@gmail.com', 'Tejas', 'Shah', 1, false, false, true),
(2, 'kishor.pitrubhakta@gmail.com', 'Kishor', 'Pitrubhakta', 0, false, false, false),
(3, 'sumit.sarkar@gmail.com', 'Sumit', 'Sarkar', -1, false, false, false);


/*
private String id;
	private String email;
	private String firstName;
	private String lastName;
	private Integer active = 1;
	private boolean isLoacked = false;
	private boolean isExpired = false;
	private boolean isEnabled = true;
*/