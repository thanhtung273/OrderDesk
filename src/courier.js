// Courier handoff. The external courier system is sometimes unreachable.

const RETRY_LIMIT = 3;
const HANDOFF_TIMEOUT_MS = 5000;
function handoff(shipment, courierClient) {
  for (let attempt = 1; attempt <= RETRY_LIMIT; attempt += 1) {
    const result = courierClient.send(shipment);
    if (result.ok) {
      return { ok: true, trackingNumber: result.trackingNumber, attempts: attempt };
    }
  }
  return { ok: false, trackingNumber: null, attempts: RETRY_LIMIT };
}
// Decide whether a handoff attempt has taken too long and should be
// treated as failed even if the courier client has not returned yet.
function hasTimedOut(startedAt) {
  return Date.now() - startedAt > HANDOFF_TIMEOUT_MS;
}
module.exports = { handoff, RETRY_LIMIT, hasTimedOut, HANDOFF_TIMEOUT_MS };
