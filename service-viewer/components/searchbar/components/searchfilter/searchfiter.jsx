import React from 'react';
import { Button } from 'reactstrap';
import 'bootstrap/dist/css/bootstrap.min.css';

class SearchFilter extends React.PureComponent {
    render() {
        return (
            <div>
                <label>SEARCH BY</label>
                <Button class="btn btn-space">TITLE</Button>
                <Button class="btn btn-space">GENRE</Button>
            </div>
        );
    }
}


export default SearchFilter;