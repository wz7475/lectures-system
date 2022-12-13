import React from "react";
import logo from "../../logo.svg";
import "./Logo.css";

const Logo: React.FC = () => {
    return (
        <div className="logo-container">
            <img src={logo} alt="SWiZZLE Logo"/>
            <div className="logo-title">
                SWiZZLE
            </div>
        </div>
    );
};

export default Logo;