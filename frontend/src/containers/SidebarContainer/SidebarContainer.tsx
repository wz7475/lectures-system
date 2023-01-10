import React from "react";
import "./SidebarContainer.css";
import Logo from "../../components/Logo/Logo";
import NavigationLink from "./NavigationLink/NavigationLink";
import LogoutButton from "./LogoutButton/LogoutButton";

const SidebarContainer: React.FC = () => {
    return (
        <div className="sidebar-container">
            <div className="sidebar-container-logo">
                <Logo/>
            </div>
            <div className="sidebar-container-navigation">
                <NavigationLink to="/lectures">
                    <>
                        <i className="icon-list"/>
                        Lectures
                    </>
                </NavigationLink>
                <NavigationLink to="/switches">
                    <>
                        <i className="icon-exchange"/>
                        Offers
                    </>
                </NavigationLink>
                <NavigationLink to="/profile">
                    <>
                        <i className="icon-user"/>
                        My Profile
                    </>
                </NavigationLink>
            </div>
            <div className="sidebar-container-logout">
                <LogoutButton/>
            </div>
        </div>
    )
};

export default SidebarContainer;