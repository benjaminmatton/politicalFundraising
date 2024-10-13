import React, { useState } from 'react';
import axios from 'axios';

// Component for searching issues and displaying related candidates
const IssueSearch = () => {
    // State variables for queries, candidates, and error handling
    const [issueQuery, setIssueQuery] = useState(''); // For searching issues
    const [candidateQuery, setCandidateQuery] = useState(''); // For searching candidates
    const [candidates, setCandidates] = useState([]);
    const [error, setError] = useState(null);

    // Function to handle the search action for issues
    const handleIssueSearch = async () => {
        try {
            // Make API call to fetch candidates for the given issue
            const response = await axios.get(`http://localhost:8080/api/issues/${issueQuery}/candidates`);
            setCandidates(response.data);
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
            setCandidates(response.data);
            setError(null);
        } catch (err) {
            // Handle errors if the API call fails
            setError('Could not fetch candidates with the specified name.');
            console.error(err);
        }
    };

    return (
        <div className="issue-search">
            <h2>Search for Issues or Candidates</h2>
            
            {/* Input and button for searching by issue */}
            <div>
                <input
                    type="text"
                    placeholder="Enter issue name"
                    value={issueQuery}
                    onChange={(e) => setIssueQuery(e.target.value)}
                />
                <button onClick={handleIssueSearch}>Search by Issue</button>
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
                <h3>{issueQuery ? `Candidates for Issue: ${issueQuery}` : 'Search Results'}</h3>
                {/* Conditional rendering based on candidates array */}
                {candidates.length > 0 ? (
                    <ul>
                        {/* Map through candidates and display their information */}
                        {candidates.map((candidate) => (
                            <li key={candidate.id}>
                                <p>Name: {candidate.name}</p>
                                <p>Score: {candidate.issueScores[issueQuery]}</p>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>No candidates found for this search.</p>
                )}
            </div>
        </div>
    );
};

export default IssueSearch;
