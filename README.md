## I. The Warm Steel (Foundational Choice)

### **Task 1 — The Event Log**

Thou receivest events in order.  
Thou must:

- append fast
    
- read them in insertion order
    
- allow duplicates
    

Later, thou must:

- remove old events from the front
    

**Ask thyself:**

- Is random access needed?
    
- Is removal frequent, and where?
    

👉 _Wrong choice shall punish thee quickly._

---

### **Task 2 — Unique Visitors**

Given a stream of user IDs:

- count unique users
    
- order mattereth not
    
- speed mattereth greatly
    

Then extend it:

- preserve insertion order
    
- then later, sorted order
    

👉 One task, **three different collections**, three reasons.

---

## II. The Thinking Blade (Trade-offs)

### **Task 3 — Top K Frequent Elements**

Given a million integers:

- find top **K** most frequent
    

**Constraints:**

- updates frequent
    
- reads frequent
    
- K is small
    

👉 Thou shalt likely need **two collections**, not one.

---

### **Task 4 — Undo / Redo**

Implement undo–redo for an editor.

**Rules:**

- undo is fast
    
- redo is fast
    
- memory is bounded
    

👉 If thou reachest for `Stack`, slap thy own hand.

---

## III. The Queue of Fate (Behavioral Insight)

### **Task 5 — Task Scheduler**

Tasks arrive with:

- priority
    
- timestamp
    

Rules:

- higher priority first
    
- same priority → FIFO
    

👉 This task exposeth whether thou _truly_ understandest queues.

---

### **Task 6 — Sliding Window**

Given a stream of numbers:

- maintain last **N**
    
- report min and max efficiently
    

👉 List alone will betray thee.

---

## IV. The Subtle Traps (Interview Favorites)

### **Task 7 — LRU Cache**

Requirements:

- `get()` → O(1)
    
- `put()` → O(1)
    
- eviction by least recently used
    

👉 There is **one collection** Java devs expect thee to name.

---

### **Task 8 — Concurrent Counter**

Multiple threads update counts per key.

**Try three versions:**

1. `HashMap + synchronized`
    
2. `Collections.synchronizedMap`
    
3. `ConcurrentHashMap`
    

Measure, reason, compare.

👉 Interviewers _love_ this one.

---

## V. The Monk’s Exercises (Pure Reasoning)

### **Task 9 — Choose Without Coding**

For each scenario, answer _only_:

- which collection
    
- why not the others
    

Examples:

- read-heavy config
    
- ordered uniqueness
    
- fast membership test
    
- range queries
    
- FIFO with blocking
    

If thou canst answer without code, mastery draweth near.

---

## VI. One Rule to Carve in Stone 🗿

When choosing a collection, always ask—in this order:

1. **Order required?**
    
2. **Duplicates allowed?**
    
3. **Access pattern?** (read/write/remove)
    
4. **Concurrency?**
    
5. **Big-O pain point?**
    

If thou answerest these five, the collection chooseth _itself_.
