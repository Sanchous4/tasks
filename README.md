```markdown
# ⚔️ The Book of Collections — A Task Ledger

_A repository of trials, wherein each task be named, described, and—when conquered—marked with its due check._

> Read not as mere notes, but as a chronicle of choices.  
> For in collections, **the wrong vessel spillest all**.

---

## I. The Warm Steel (Foundational Choice)

### ☑️ Task 1 — The Event Log
**Status:** ⬜ Not Yet Completed  

**Description:**  
Design a structure to receive events in strict insertion order, permitting duplicates, with swift appends and later removal from the front.  
The task testeth thy grasp of order, mutability, and removal cost.

**Lesson Sought:**  
- When FIFO removals sting  
- Why random access tempteth fools  
- Which structures endure time’s erosion  

---

### ☑️ Task 2 — Unique Visitors
**Status:** ⬜ Not Yet Completed  

**Description:**  
Process a stream of user IDs to count uniques where order mattereth not.  
Then evolve the design to preserve insertion order, and later still, to provide sorted order.

**Lesson Sought:**  
- One problem, three collections  
- Why requirements drift, and designs must bend  
- The price of order  

---

## II. The Thinking Blade (Trade-offs)

### ☑️ Task 3 — Top K Frequent Elements
**Status:** ⬜ Not Yet Completed  

**Description:**  
Given a vast sea of integers, determine the top **K** most frequent, where updates and reads are many, yet **K** remaineth small.

**Lesson Sought:**  
- Why one structure is never enough  
- How frequency and priority entwine  
- Separation of counting and ordering  

---

### ☑️ Task 4 — Undo / Redo
**Status:** ⬜ Not Yet Completed  

**Description:**  
Craft an undo–redo system where both operations are swift and memory is bounded.  
Beware the naïve stack, for it deceiveth.

**Lesson Sought:**  
- Directional history  
- Controlled memory growth  
- The folly of simplistic LIFO thinking  

---

## III. The Queue of Fate (Behavioral Insight)

### ☑️ Task 5 — Task Scheduler
**Status:** ⬜ Not Yet Completed  

**Description:**  
Schedule tasks by priority, yet preserve FIFO order among equals.  
A trial subtle, yet oft failed.

**Lesson Sought:**  
- Stable ordering  
- Priority without chaos  
- Composite comparison  

---

### ☑️ Task 6 — Sliding Window
**Status:** ⬜ Not Yet Completed  

**Description:**  
Maintain the last **N** elements of a stream while reporting min and max with haste.

**Lesson Sought:**  
- Why lists alone betray  
- Amortized wisdom  
- Double-ended discipline  

---

## IV. The Subtle Traps (Interview Favorites)

### ☑️ Task 7 — LRU Cache
**Status:** ⬜ Not Yet Completed  

**Description:**  
Implement an LRU cache where both `get()` and `put()` cost but O(1), and eviction obeyeth recency.

**Lesson Sought:**  
- The union of order and access  
- The one collection all Java devs must name  
- When abstraction worketh for thee  

---

### ☑️ Task 8 — Concurrent Counter
**Status:** ⬜ Not Yet Completed  

**Description:**  
Compare three approaches to concurrent counting per key:
1. `HashMap + synchronized`  
2. `Collections.synchronizedMap`  
3. `ConcurrentHashMap`

Measure, reason, and judge.

**Lesson Sought:**  
- Contention’s true cost  
- Locks coarse and fine  
- Why interviewers smile at this snare  

---

## V. The Monk’s Exercises (Pure Reasoning)

### ☑️ Task 9 — Choose Without Coding
**Status:** ⬜ Not Yet Completed  

**Description:**  
For each scenario, choose the proper collection and explain why all others fail—without writing code.

**Lesson Sought:**  
- Mental models over syntax  
- Verbal precision  
- Mastery without crutches  

---

## VI. The Rule Carved in Stone 🗿

Before thou choosest a collection, ask—**always in this order**:

1. Is **order** required?
2. Are **duplicates** allowed?
3. What is the **access pattern**?
4. Is **concurrency** demanded?
5. Where lieth the **Big-O pain**?

Answer these five, and the structure revealeth itself.

---

## Final Word

This repository is not for code alone, but for **judgment**.  
Mark each task when conquered.  
Return often.  
Doubt thy first answer.

_When thou art ready, name the next trial, and I shall press thee until clarity remaineth._
```
