
class User {
    username;
    password;
    id;
    collection;
    constructor(username, password) {
        this.username = username;
        this.password = password;
        this.id = User.incrementId()
        this.collection = []
    }

    static incrementId() {
        if (!User.latestId) {
            User.latestId = 1;
        } else {
            User.latestId += 1;
        }
        return User.latestId;
    }
}

export default User;

export const Users = {
    users: []
}