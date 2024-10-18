import React, { useState, useEffect } from 'react';
import axios from 'axios';

// Component for searching issues and displaying related candidates
const IssueSearch = () => {
    // State variables for queries, candidates, issues, and error handling
    const [issueQuery, setIssueQuery] = useState(''); // For searching issues
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

    // Function to handle the search action for issues
    const handleIssueSearch = async () => {
        if (!selectedIssue) {
            setError('Please select an issue.');
            return;
        }
        try {
            // Make API call to fetch candidates for the given issue
            const encodedIssue = encodeURIComponent(selectedIssue);
            const response = await axios.get(`http://localhost:8080/api/issues/${encodedIssue}/candidates`);
            const allCandidates = Object.entries(response.data).map(([candidateJson, score]) => {
                const candidateObj = JSON.parse(candidateJson); // Parse the serialized JSON string back to an object
                return {
                    id: candidateObj._id, // Include the candidate's ID
                    name: candidateObj.name,
                    score // This is the effectiveness score
                };
            });
            setCandidates(allCandidates);
            // Sort candidates by score and get top 10
            const sortedCandidates = [...allCandidates].sort((a, b) => b.score - a.score).slice(0, 10);
            setTopCandidates(sortedCandidates);
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
            setCandidates(response.data.map(candidate => ({
                id: candidate._id,
                name: candidate.name,
                score: candidate.score || 'N/A'
            })));
            setTopCandidates([]);
            setError(null);
        } catch (err) {
            // Handle errors if the API call fails
            setError('Could not fetch candidates with the specified name.');
            console.error(err);
        }
    };

    return (
        <div className="issue-search" style={{ display: 'flex' }}>
            <div style={{ flex: 1 }}>
                <h2>Search for Issues or Candidates</h2>
                
                {/* Dropdown for selecting an issue */}
                <div>
                    <select
                        value={selectedIssue}
                        onChange={(e) => {
                            setSelectedIssue(e.target.value);
                            if (e.target.value) {
                                handleIssueSearch();
                            }
                        }}
                    >
                        <option value="">Select an issue</option>
                        {issues.map((issue) => (
                            <option key={issue.id} value={issue.name}>{issue.name}</option>
                        ))}
                    </select>
                </div>
                
                {/* Display list of possible issues */}
                <div style={{ marginTop: '20px' }}>
                    <h3>Possible Issues:</h3>
                    <ul>
                        {issues.map((issue) => (
                            <li key={issue.id}>{issue.name}</li>
                        ))}
                    </ul>
                </div>
                
                {/* Input and button for searching by candidate */}
                <div style={{ marginTop: '20px' }}>
                    <input
                        type="text"
                        placeholder="Enter candidate name"
                        value={candidateQuery}
                        onChange={(e) => setCandidateQuery(e.target.value)}
                    />
                    <button onClick={handleCandidateSearch}>Search by Candidate</button>
                </div>

                {/* Display error message if there's an error */}
                {error && <p style={{ color: 'red', marginTop: '20px' }}>{error}</p>}

                <div className="candidate-list" style={{ marginTop: '20px' }}>
                    <h3>{selectedIssue ? `All Candidates for Issue: ${selectedIssue}` : 'Search Results'}</h3>
                    {/* Conditional rendering based on candidates array */}
                    {candidates.length > 0 ? (
                        <ul>
                            {/* Map through candidates and display their information */}
                            {candidates.map((candidate) => (
                                <li key={candidate.id}>
                                    <p>Name: {candidate.name}</p>
                                    <p>ID: {candidate.id}</p>
                                    <p>Score: {candidate.score}</p>
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No candidates found for this search.</p>
                    )}
                </div>
            </div>
            
            {/* Display top 10 candidates to the right */}
            <div style={{ flex: 1, marginLeft: '20px' }}>
                <h3>Top 10 Candidates for Selected Issue</h3>
                {topCandidates.length > 0 ? (
                    <ul>
                        {topCandidates.map((candidate) => (
                            <li key={candidate.id}>
                                <p>Name: {candidate.name}</p>
                                <p>ID: {candidate.id}</p>
                                <p>Score: {candidate.score}</p>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No top candidates to display. Please select an issue.</p>
                )}
            </div>
        </div>
    );
};

export default IssueSearch;
