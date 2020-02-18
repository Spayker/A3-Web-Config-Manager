import React from 'react';
import { Button } from 'reactstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './searchfilter.css';

class SearchFilter extends React.PureComponent {
    render() {
        return (
            <div className="searchFilter">
                <label>SEARCH BY: </label>
                <Button className="filterButton">TITLE</Button>
                <Button className="filterButton">GENRE</Button>
            </div>
        );
    }
}

export default SearchFilter;