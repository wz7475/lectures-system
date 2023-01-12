import {Id, useGetOpinionQuery} from "../../store/services/api";
import Loading from "../Loading/Loading";
import React from "react";
import FetchError from "../FetchError/FetchError";
import "./OpinionComponent.css";
import {IParentComponentProps} from "../../common/parentComponentProps";

interface IOpinionProps extends IParentComponentProps {
    id: Id;
}

const OpinionComponent: React.FC<IOpinionProps> = (props) => {
    const {data, isFetching} = useGetOpinionQuery(props.id);

    if (isFetching) {
        return <Loading/>;
    }

    if (data === undefined) {
        return (
            <FetchError>
                <span>Cannot fetch information about opinion</span>
            </FetchError>
        );
    }

    return (
        <div className="opinion-element">
            <div className="opinion-element-author">User#{data.userId}</div>
            <div className="opinion-element-date">{new Date(data.createdAt).toUTCString()}</div>
            <div className="opinion-element-content">{data.content}</div>
            <div className="opinion-element-controls">
                {props.children}
            </div>
        </div>
    );
}

export default OpinionComponent;