package models.DTO;

        public class CreateUseriDTO {
        private String username;
        private String name;
        private String lastname;
        private String salt;
        private String saltedPassword;

        public CreateUseriDTO(
        String username,
        String name,
        String lastname,
        String salt,
        String saltedPassword) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.salt = salt;
        this.saltedPassword = saltedPassword;
        }


            public String getUsername() {
        return username;
        }

        public void setUsername(String username) {
        this.username = username;
        }



        public String getName() {
        return name;
        }

        public void setName(String name) {
        this.name = name;
        }

        public String getLastname() {
        return lastname;
        }

        public void setLastname(String lastname) {
        this.lastname = lastname;
        }

        public String getSalt() {
        return salt;
        }

        public void setSalt(String salt) {
        this.salt = salt;
        }

        public String getSaltedPassword() {
        return saltedPassword;
        }

        public void setSaltedPassword(String saltedPassword) {
        this.saltedPassword = saltedPassword;
        }
        }
