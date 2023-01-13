import React from "react";
import logo from "../../logo.svg";
import "./Logo.css";

const Logo: React.FC = () => {
    return (
        <div className="logo-container">
            <img src={logo} alt="SWiZLE Logo"/>
            <div className="logo-title">
                SWiZLE
            </div>
        </div>
    );
};

export default Logo;