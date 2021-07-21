package main

const (
	ALPHABET_SIZE = 26
)

type Trie struct {
	root *trieNode
}

type trieNode struct {
	isWordEnd bool
	children  [ALPHABET_SIZE]*trieNode
}

/** Initialize your data structure here. */
func Constructor() Trie {
	return Trie{root: &trieNode{}}
}

/** Inserts a word into the trie. */
func (this *Trie) Insert(word string) {
	current := this.root
	for i := 0; i < len(word); i++ {
		index := word[i] - 'a'
		if current.children[index] == nil {
			current.children[index] = &trieNode{}
		}
		current = current.children[index]
	}
	current.isWordEnd = true
}

/** Returns if the word is in the trie. */
func (this *Trie) Search(word string) bool {
	current := this.root
	for i := 0; i < len(word); i++ {
		index := word[i] - 'a'
		if current.children[index] == nil {
			return false
		}
		current = current.children[index]
	}
	if current.isWordEnd {
		return true
	}
	return false
}

/** Returns if there is any word in the trie that starts with the given prefix. */
func (this *Trie) StartsWith(prefix string) bool {
	current := this.root
	for i := 0; i < len(prefix); i++ {
		index := prefix[i] - 'a'
		if current.children[index] == nil {
			return false
		}
		current = current.children[index]
	}

	return true
}

/**
 * Your Trie object will be instantiated and called as such:
 * obj := Constructor();
 * obj.Insert(word);
 * param_2 := obj.Search(word);
 * param_3 := obj.StartsWith(prefix);
 */
