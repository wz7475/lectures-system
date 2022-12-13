import React from "react";
import "./NavigationLink.css";
import {NavLink} from "react-router-dom";

interface NavigationLinkProps {
    title: string;
    to: string;
    disabled?: boolean;
}

const NavigationLink: React.FC<NavigationLinkProps> = ({title, to, disabled = false}) => {
    if (disabled) {
        return (
            <div className="navigation-link disabled">
                {title}
                <div className="temporary-disabled">Temporary disabled</div>
            </div>
        );
    }

    return (
        <NavLink to={to} className={({isActive}) => isActive ? "navigation-link active" : "navigation-link"}>
            {title}
        </NavLink>
    );
};

export default NavigationLink;