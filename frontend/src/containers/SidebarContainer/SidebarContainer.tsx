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
                <NavigationLink title="Lectures" to="/lectures"/>
                <NavigationLink title="Opinions" to="/opinions"/>
                <NavigationLink title="Switch" to="/switches"/>
                <NavigationLink title="Profile" to="/profile"/>
            </div>
            <div className="sidebar-container-logout">
                <LogoutButton/>
            </div>
        </div>
    )
};

export default SidebarContainer;