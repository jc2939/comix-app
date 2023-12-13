import { createContext, useContext, useState } from 'react';

export const UserContext = createContext(undefined);

export const UserProvider = ({ children }) => {
    const [currentUser, setCurrentUser] = useState('Guest');
    const [loginStatus, setLoginStatus] = useState(false)

    const setUser = (user) => {
        setCurrentUser(user);
    };
    const setLogin = (loggedIn) => {
        setLoginStatus(loggedIn)
    }

    return (
        <UserContext.Provider value={{ currentUser, setUser, loginStatus, setLogin }}>
            {children}
        </UserContext.Provider>
    );
};

export const useUser = () => {
    const context = useContext(UserContext);
    if (!context) {
        throw new Error('useUser must be used within a UserProvider');
    }
    return [context.currentUser, context.setUser];
};

export const useLogin = () => {
    const context = useContext(UserContext);
    if  (!context) {
        throw new Error('useLogin must be used in a UserProvider')
    }
    return [context.loginStatus, context.setLogin]
}