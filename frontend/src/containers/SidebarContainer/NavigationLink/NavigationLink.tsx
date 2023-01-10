import React from "react";
import "./NavigationLink.css";
import {NavLink} from "react-router-dom";
import {IComponentWithChildrenProps} from "../../../common/componentWithChildrenProps";

interface INavigationLinkProps extends IComponentWithChildrenProps {
    to: string;
}

const NavigationLink: React.FC<INavigationLinkProps> = ({to, children}) => {
    return (
        <NavLink to={to} className={({isActive}) => isActive ? "navigation-link active" : "navigation-link"}>
            {children}
        </NavLink>
    );
};

export default NavigationLink;