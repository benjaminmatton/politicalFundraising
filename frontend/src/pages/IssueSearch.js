import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Header from '../components/Header'; 
import '../style/IssueSearch.css'; // Make sure to create this CSS file

// Component for searching issues and displaying related candidates
const IssueSearch = () => {
    // State variables for queries, candidates, issues, and error handling
    const [candidateQuery, setCandidateQuery] = useState(''); // For searching candidates
    const [candidates, setCandidates] = useState([]);
    const [issues, setIssues] = useState([]);
    const [selectedIssue, setSelectedIssue] = useState('');
    const [error, setError] = useState(null);
    const [topCandidates, setTopCandidates] = useState([]);

    // Fetch all issues when component mounts
    useEffect(() => {
        const fetchIssues = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/issues');
                setIssues(response.data);
            } catch (err) {
                let errorMessage = 'Could not fetch issues.';
                // Check if the error has a response
                if (err.response) {
                    // The request was made and the server responded with a status code
                    // that falls out of the range of 2xx
                    errorMessage = `Error: ${err.response.status} - ${err.response.data.message || err.response.data}`;
                } else if (err.request) {
                    // The request was made but no response was received
                    errorMessage = 'Error: No response received from the server.';
                } else {
                    // Something happened in setting up the request that triggered an Error
                    errorMessage = `Error: ${err.message}`;
                }
                setError(errorMessage);
                console.error(err);
            }
        };
        fetchIssues();
    }, []);

    // Modify the onChange handler for the select element
    const handleIssueChange = (e) => {
        const newIssue = e.target.value;
        setSelectedIssue(newIssue);
        setError(null);
        if (newIssue) {
            // Use the new value directly instead of relying on state
            handleIssueSearch(newIssue);
        } else {
            setCandidates([]);
            setTopCandidates([]);
        }
    };

    // Function to handle the search action for issues
    const handleIssueSearch = async (selectedIssue) => {
        if (!selectedIssue) {
            setError('Please select an issue.');
            return;
        }
        try {
            // Make API call to fetch candidates for the given issue
            const encodedIssue = encodeURIComponent(selectedIssue);
            const response = await axios.get(`http://localhost:8080/api/issues/${encodedIssue}/candidates`);
            const topCandidates = response.data.map(candidate => ({
                id: candidate._id,
                name: candidate.name,
                score: candidate.issueScores[selectedIssue] || 'N/A'
            }));
            setTopCandidates(topCandidates);
            setError(null);
        } catch (err) {
            // Handle errors if the API call fails
            setError('Could not fetch candidates for the selected issue.');
            console.error(err);
        }
    };

    // Function to handle the search action for candidates
    const handleCandidateSearch = async () => {
        try {
            // Make API call to fetch candidates by name
            const response = await axios.get(`http://localhost:8080/api/candidates/search?name=${candidateQuery}`);
            const candidatesData = response.data.map(candidate => ({
                id: candidate._id,
                name: candidate.name,
                score: selectedIssue ? (candidate.issueScores[selectedIssue] || 'N/A') : null
            }));
            setCandidates(candidatesData);
            setError(null);
        } catch (err) {
            // Handle errors if the API call fails
            setError('Could not fetch candidates with the specified name.');
            console.error(err);
        }
    };

    return (
        <div>
            <Header />
            <div className="issue-search">
                <div className="search-section">
                    <h2>Search for Issues or Candidates</h2>
                    
                    <div className="search-and-results">
                        {/* Dropdown for selecting an issue */}
                        <div className="issue-dropdown">
                            <select
                                value={selectedIssue}
                                onChange={handleIssueChange}
                            >
                                <option value="">Select an issue</option>
                                {issues.map((issue) => (
                                    <option key={issue.id} value={issue.name}>{issue.name}</option>
                                ))}
                            </select>
                        </div>
                        
                        {/* Display candidates in a card grid */}
                        <div className="candidates-section">
                            <h3>Candidates for {selectedIssue || 'Selected Issue'}</h3>
                            <div className="candidate-grid">
                                {candidates.length > 0 ? (
                                    candidates.map((candidate) => (
                                        <div key={candidate.id} className="candidate-card">
                                            <h4>{candidate.name}</h4>
                                            {candidate.score !== null && (
                                                <p>Score for {selectedIssue}: {typeof candidate.score === 'number' ? candidate.score.toFixed(2) : candidate.score}</p>
                                            )}
                                        </div>
                                    ))
                                ) : (
                                    <p>No candidates to display. Please search for candidates.</p>
                                )}
                            </div>
                        </div>
                    </div>
                    
                    {/* Input and button for searching by candidate */}
                    <div className="candidate-search">
                        <input
                            type="text"
                            placeholder="Enter candidate name"
                            value={candidateQuery}
                            onChange={(e) => setCandidateQuery(e.target.value)}
                        />
                        <button onClick={handleCandidateSearch}>Search by Candidate</button>
                    </div>

                    {/* Display error message if there's an error */}
                    {error && <p className="error-message">{error}</p>}
                </div>
                
                {/* Display top 10 candidates */}
                <div className="top-candidates-section">
                    <h3>Top 10 Candidates for {selectedIssue || 'Selected Issue'}</h3>
                    <div className="candidate-grid">
                        {topCandidates.length > 0 ? (
                            topCandidates.map((candidate) => (
                                <div key={candidate.id} className="candidate-card">
                                    <h4>{candidate.name}</h4>
                                    <p>Score: {candidate.score}</p>
                                </div>
                            ))
                        ) : (
                            <p>No top candidates to display. Please select an issue.</p>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default IssueSearch;
