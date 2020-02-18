import React from 'react';
import { Button } from 'reactstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './infobar.css';

class InfoBoard extends React.Component {
    render() {
        return (
            <div className="rowC infobar">
                <span>{ this.props.total }</span>
                <div>
                    <span>Sort By:</span>
                    <Button className="sorting">release date</Button>
                    <Button className="sorting">rating</Button>
                </div>
            </div>
        );
    }
}

export default InfoBoard;