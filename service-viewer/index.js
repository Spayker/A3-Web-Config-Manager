import React from 'react';
import ReactDOM from 'react-dom';
import {SearchBar, FilmList} from './components';

const APP_TITLE = React.createElement(
    "title", 
    {classname: 'title'}, 
    "Netflix Movie Searcher"
);

class App extends React.Component{
    render(){
        return(
            <div class="col-xs-12 col-sm-12">
                <SearchBar/>
                <FilmList/>
            </div>
        );
    }
}

function footer() {
    return (
        <div class="col-xs-12 col-sm-12">
            <h2>Footer Section</h2>
        </div>
    );
}

const root = React.createElement("div", null, APP_TITLE, <App/>, footer());

ReactDOM.render(
    root,
    document.getElementById('root')
)
