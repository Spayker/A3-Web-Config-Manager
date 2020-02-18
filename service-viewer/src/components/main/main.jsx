import React from 'react'
import {
	SearchBar,
	FilmListComponent,
	Footer,
	ErrorBoundary
} from '../../components/';

export default class Main extends React.Component {
    constructor(props) {
      super(props);
    }
  
    render() {
        return(
            <div>
                <ErrorBoundary>
                    <div className="header">
                        <SearchBar/>
                    </div>
                    <div className="content">
                        <div className="detail">
                            <FilmListComponent/>
                        </div>
                    </div>
                    <div className="footer">
                        <Footer/>
                    </div>
                </ErrorBoundary>
            </div>
		);
    }
}