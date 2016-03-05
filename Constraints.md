here one template: <br><br>
<i><b>Constraint:</b> A short title for the constraint, i.e. "Caching is only allowed on client side"</i><br>
<i><b>Definition:</b> What does it exactly mean?</i><br>
<i><b>Rationale:</b> What is the reason for this constraint?</i><br>
<i><b>Implications:</b> What does it mean for the architecture? What are the limitations it places?</i><br>

<h2>and now the actual constraints for cdo-light:</h2>
<i><b>Constraint:</b> Caching is only allowed on client side.</i><br>
<i><b>Definition:</b> When a CDORevision is queried on the server side, there is no lookup for this revision in the revision cache. Instead of searching in the cache, the query is directly forwarded to the store.</i><br>
<i><b>Rationale:</b> The reason for this is to have a lower heap consumption.</i><br>
<i><b>Implications:</b> This has an effect to the revision manager which uses the cache. This means also, that the MemStore cannot be use, because it caches the revisions.</i><br>
<hr />